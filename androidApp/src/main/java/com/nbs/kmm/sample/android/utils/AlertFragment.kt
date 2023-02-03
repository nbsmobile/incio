package com.nbs.kmm.sample.android.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.utils.util.visible

class AlertFragment : BottomSheetDialogFragment() {

    private var retryAction: () -> Unit = {}
    private var secondaryAction: () -> Unit = {}
    private var errorMessage = ""
    private var btnRetryTitle = ""
    private var secondaryBtnTitle = ""

    companion object {
        private const val KEY_ERROR_MESSAGE = "error_message"

        fun newInstance(
            errorMessage: String,
        ): AlertFragment {

            val bundle = Bundle()
            bundle.putString(KEY_ERROR_MESSAGE, errorMessage)
            val fragment = AlertFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    private fun handleArguments() {
        errorMessage = arguments?.getString(KEY_ERROR_MESSAGE, null) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvErrorMessage = view.findViewById<TextView>(R.id.tvErrorMessage)
        val btnRetry = view.findViewById<TextView>(R.id.btnRetry)
        val btnSecondaryCta = view.findViewById<Button>(R.id.btnSecondaryCta)

        tvErrorMessage.text = errorMessage

        btnRetry.text = if (btnRetryTitle.isNotEmpty()) {
            btnRetryTitle
        } else {
            "Coba lagi"
        }

        if (secondaryBtnTitle.isEmpty().not()) {
            btnSecondaryCta.visible()
            btnSecondaryCta.text = secondaryBtnTitle
        }


        btnRetry.setOnClickListener {
            retryAction()
            dismiss()
        }

        btnSecondaryCta.setOnClickListener {
            secondaryAction()
            dismiss()
        }
    }

    fun setRetryAction(retryAction: () -> Unit) {
        this.retryAction = retryAction
    }


    fun setRetryButtonTitle(title: String) {
        btnRetryTitle = title
    }

    fun setSecondaryButtonTitle(title: String) {
        secondaryBtnTitle = title
    }

    fun setSecondaryAction(secondaryCta: () -> Unit) {
        this.secondaryAction = secondaryCta
    }
}
