package com.rodda.detailmodule.ui.dataform

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodda.detailmodule.R

class DataFormFragment : Fragment() {

    companion object {
        fun newInstance() = DataFormFragment()
    }

    private lateinit var viewModel: DataFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.data_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DataFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

}