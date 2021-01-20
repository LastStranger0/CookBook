package com.mtg.cookbook.presenter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.mtg.cookbook.R
import com.mtg.cookbook.model.Step


class StepsAdapter(context: Context, var steps: MutableList<Step>): RecyclerView.Adapter<StepsAdapter.ViewHolder>() {
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var StepText: TextInputEditText = view.findViewById(R.id.stepsText)
        var StepName: MaterialTextView = view.findViewById(R.id.stepName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.activity_steps, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var _step = steps.get(position)
        var name = holder.StepName.text.toString()
        name += " ${(position+1)}"
        holder.StepName.text = name
        holder.StepText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                steps[position].text = holder.StepText.text.toString()
            }
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })
    }

    override fun getItemCount(): Int {
        return steps.size
    }
}