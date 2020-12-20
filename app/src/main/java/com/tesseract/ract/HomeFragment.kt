package com.tesseract.ract

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tesseract.launchersdk.appinfo.AppInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var appsList: List<AppInfo>
    private lateinit var launcherAdapter: LauncherAdapter

     val homeViewModel by viewModels<HomeViewModel>()

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            filter(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
            Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progress.isVisible = true
        observeOnAppsListLiveData()
        homeViewModel.getInstalledApps()
    }

    private fun observeOnAppsListLiveData() {
        homeViewModel.installedAppsLiveData.observe(viewLifecycleOwner, Observer { apps ->
            progress.isVisible = false
            show(apps)
        })
    }

    private fun show(apps: List<AppInfo>) {
        this.appsList = apps
        initLauncherRecyclerView()
        setAppClickListener()
        searchBar.addTextChangedListener(textWatcher)
    }

    private fun setAppClickListener() {
        launcherAdapter.onAppClickListener = { appInfo ->
            val launchIntent = appInfo.packageName?.let { pkg ->
                requireContext().packageManager.getLaunchIntentForPackage(pkg)
            }
            launchIntent?.let(::startActivity)
        }
    }

    private fun initLauncherRecyclerView() {
        launcherAdapter = LauncherAdapter(appsList, requireContext())
        rvLauncher.apply {
            adapter = launcherAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun filter(searchTerm: String) {
        val filteredApps = appsList.filter { appMatches(it, searchTerm) }
        launcherAdapter.updateList(filteredApps)
    }

    private fun appMatches(app: AppInfo, searchTerm: String) =
        app.name?.toLowerCase(Locale.getDefault())?.replace("\\s+", "")
            ?.contains(searchTerm.toLowerCase(Locale.getDefault()).replace("\\s+", "")) == true

    companion object {
        fun newInstance() = HomeFragment()
    }
}
