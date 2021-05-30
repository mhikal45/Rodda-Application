package com.rodda.detailmodule.ui.imagemain

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodda.detailmodule.R

class ImageMainFragment : Fragment() {

    companion object {
        fun newInstance() = ImageMainFragment()
    }

    private lateinit var viewModel: ImageMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.image_main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImageMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}