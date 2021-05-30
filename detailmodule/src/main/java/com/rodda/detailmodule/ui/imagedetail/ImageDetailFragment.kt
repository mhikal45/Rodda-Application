package com.rodda.detailmodule.ui.imagedetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodda.detailmodule.R

class ImageDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ImageDetailFragment()
    }

    private lateinit var viewModel: ImageDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.image_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImageDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}