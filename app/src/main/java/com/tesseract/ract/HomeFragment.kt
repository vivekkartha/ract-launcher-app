package com.tesseract.ract

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tesseract.launchersdk.LauncherSdk
import com.tesseract.launchersdk.appinfo.AppInfo
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.Locale

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var appsList: List<AppInfo>
    private lateinit var launcherAdapter: LauncherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        appsList = LauncherSdk.getInstance(requireContext()).getInstalledApps()
        launcherAdapter = LauncherAdapter(appsList)
        rvLauncher.apply {
            adapter = launcherAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })
    }

    private fun filter(searchTerm: String) {
        val filteredApps = appsList.filter { appMatches(it, searchTerm) }
        launcherAdapter.updateList(filteredApps)
    }

    private fun appMatches(app: AppInfo, searchTerm: String) =
        app.name?.toLowerCase(Locale.getDefault())
            ?.contains(searchTerm.toLowerCase(Locale.getDefault())) == true
}