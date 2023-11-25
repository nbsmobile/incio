package com.nbs.kmm.sample.android.base

import android.accounts.AccountManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.scheduler.mainScheduler
import com.nbs.kmm.sample.android.presentation.reuse.CustomSampleLoading
import com.nbs.kmm.shared.utils.eventbus.Event
import com.nbs.kmm.shared.utils.eventbus.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

abstract class SampleBaseActivity<VB : ViewBinding> : AppCompatActivity(), CoroutineScope,
    BaseView {
    abstract fun getViewBinding(): VB
    lateinit var binding: VB

    abstract fun initIntent()

    abstract fun initUI()

    abstract fun initAction()

    abstract fun initProses()

    abstract fun initObservers()

    private val accountManager: AccountManager by inject()

//    private val membershipViewModel: MembershipViewModel by inject()

    private val loading: CustomSampleLoading by lazy { CustomSampleLoading(this) }

//    private val disposable: CompositeDisposable by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initIntent()
        initUI()
        initAction()
        initProses()
        initObservers()
        onViewReady()
    }

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        if (loading.isShowing) {
            loading.cancel()
        }
    }

    override fun setupToolbar(toolbar: Toolbar, isChild: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun setupToolbar(toolbar: Toolbar, title: String, isChild: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.title = title
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var unauthorizedJob: Job? = null

    private fun onViewReady() {
        EventBus.subscribe<Event.UserUnauthorized>()
            .observeOn(mainScheduler)
            .subscribe {
                unauthorizedJob?.cancel()
                unauthorizedJob = launch {
//                    FirebaseMessaging.getInstance().deleteToken()
//                    finishAffinity()
//                    SessionExpiredPageActivity.start(this@SampleBaseActivity)
//                    delay(5000)
                }
            }
//            .addTo(disposable)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (!disposable.isDisposed) {
//            disposable.dispose()
    }
}
