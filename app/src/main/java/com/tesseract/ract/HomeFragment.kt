package com.tesseract.ract

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tesseract.launchersdk.LauncherSdk
import com.tesseract.launchersdk.appinfo.AppInfo
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.Locale

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appsList = LauncherSdk.getInstance(requireContext()).getInstalledApps()
        initLauncherRecyclerView()
        searchBar.addTextChangedListener(textWatcher)
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
        app.name?.toLowerCase(Locale.getDefault())
            ?.contains(searchTerm.toLowerCase(Locale.getDefault())) == true

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var appsList: List<AppInfo>
    private lateinit var launcherAdapter: LauncherAdapter
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            filter(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
            Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }
}
