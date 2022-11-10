package com.cooper.firebasesdk.module.addMeeting.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.cooper.firebasesdk.R
import com.cooper.firebasesdk.common.BaseActivity
import com.cooper.firebasesdk.common.Constants
import com.cooper.firebasesdk.databinding.ActivityAddAndEditNoteBinding
import com.cooper.firebasesdk.module.addMeeting.dto.ResultData
import com.cooper.firebasesdk.module.listing.adapter.ClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddMeetingActivity : BaseActivity<ActivityAddAndEditNoteBinding, AddMeetingViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refNo = intent.getStringExtra(Constants.REF_ID) ?: ""
        viewModel.updateEditValue()
        initView()
        addObserver()
    }

    private fun initView(){
        if(viewModel.isEdit.value == true && intent.getSerializableExtra(Constants.RESULT_DATA) != null){
            val resultData =  intent.getSerializableExtra(Constants.RESULT_DATA) as ResultData
            dataBinding.title.setText(resultData.title)
            dataBinding.date.setText(resultData.date)
            dataBinding.note.setText(resultData.description)
            if(viewModel.isEdit.value == true){
                dataBinding.btnSubmit.text = "Edit"
            }else{
                dataBinding.btnSubmit.text = "Add"
            }
        }
    }

    override fun getBindingViewId() = R.layout.activity_add_and_edit_note

    override fun getViewModelClass() = AddMeetingViewModel::class.java

    private fun addObserver() {
        viewModel.isSuccess.observe(this) {
            if (it) {
                finish()
            }
        }
    }

    fun onClick(view: View?) {
        viewModel.callApi(ResultData(dataBinding.title.text.toString(), dataBinding.date.text.toString(),
        dataBinding.note.text.toString()))
    }

    fun openDatePicker(view: View?){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->
            dataBinding.date.setText("$dayOfMonth-$monthOfYear-$year")
        }, year, month, day)
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }

}