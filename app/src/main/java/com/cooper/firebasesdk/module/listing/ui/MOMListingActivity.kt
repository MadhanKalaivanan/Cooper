package com.cooper.firebasesdk.module.listing.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.cooper.firebasesdk.R
import com.cooper.firebasesdk.common.BaseActivity
import com.cooper.firebasesdk.common.Constants
import com.cooper.firebasesdk.databinding.ActivityMainBinding
import com.cooper.firebasesdk.module.addMeeting.dto.ResultData
import com.cooper.firebasesdk.module.addMeeting.ui.AddMeetingActivity
import com.cooper.firebasesdk.module.listing.adapter.ClickListener
import com.cooper.firebasesdk.module.listing.adapter.ListAdapter
import com.cooper.firebasesdk.module.listing.adapter.WrapContentLinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MOMListingActivity : BaseActivity<ActivityMainBinding, MOMListViewModel>(), ClickListener {

    @Inject
    lateinit var database: FirebaseDatabase

    @Inject
    lateinit var auth: FirebaseAuth

    private var listAdapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val query = database
            .reference
            .child(Constants.CHILD_NAME)
            .child(auth.currentUser?.uid ?: "")

        val options = FirebaseRecyclerOptions.Builder<ResultData>()
            .setQuery(query, ResultData::class.java)
            .build()
        listAdapter = ListAdapter(this, options)
        dataBinding.list.layoutManager = WrapContentLinearLayoutManager(this)
        dataBinding.list.adapter = listAdapter
        onClick()
    }

    override fun getBindingViewId() = R.layout.activity_main

    override fun getViewModelClass() = MOMListViewModel::class.java

    override fun onStart() {
        super.onStart()
        listAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        listAdapter?.stopListening()
    }

    private fun onClick() {
        dataBinding.fab.setOnClickListener {
            rowClickListener()
        }
    }

    override fun rowClickListener(resultData: ResultData?, id: String?) {
        startActivity(Intent(this, AddMeetingActivity::class.java)
            .putExtra(Constants.RESULT_DATA, resultData)
            .putExtra(Constants.REF_ID, id))
    }


}