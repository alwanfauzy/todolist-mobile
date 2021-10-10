package com.alwan.todolist

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


class AddDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_fragment_add, container)

        // make dialog itself transparent
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //[add more custom code here...]
        return view
    }
}