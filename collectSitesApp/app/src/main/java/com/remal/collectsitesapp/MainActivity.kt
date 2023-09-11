package com.remal.collectsitesapp

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.otaliastudios.autocomplete.Autocomplete
import com.otaliastudios.autocomplete.AutocompleteCallback
import com.remal.collectsitesapp.data.Suggestion
import com.remal.collectsitesapp.databinding.MainActBinding
import com.remal.collectsitesapp.suggestion.SuggestionPresenter
import com.remal.collectsitesapp.suggestion.SuggestionViewModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.star_zero.navigation_keep_fragment_sample.navigation.KeepStateNavigator


/**
 * Main activity for the todoapp. Holds the Navigation Host Fragment and the Drawer, Toolbar, etc.
 */
class MainActivity : AppCompatActivity() {
    var guidelinePercent = 1F

    private lateinit var binding: MainActBinding
    private var isClearHistory: Boolean = false

    override fun onBackPressed() {
        if (binding.slidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            if (binding.webview.canGoBack()) {
                val historyList = binding.webview.copyBackForwardList()
                if (historyList.currentIndex == 1 && historyList.getItemAtIndex(historyList.currentIndex - 1).url == "about:blank") {
                    binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                } else {
                    binding.webview.goBack()
                }
            } else {
                binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSetting()
        binding = DataBindingUtil.setContentView(this, R.layout.main_act)
        binding.activity = this@MainActivity
        binding.viewmodel = SuggestionViewModel()
        setSupportActionBar(findViewById(R.id.toolbar))

        MobileAds.initialize(this) {}

        val navController: NavController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
        val navigator =
            KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)

        navigator.setOnNavigationItemSelectedListener(object : KeepStateNavigator.EventListener {
            override fun onNavigateChangedListener() {
                if (binding.slidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }
            }
        })

        navController.navigatorProvider.addNavigator(navigator)
        navController.setGraph(R.navigation.nav_graph_bottom)

        binding.bottomNav.setupWithNavController(navController)
        setupWebView()
        setupSlidingUpPanelLayout()
        setupNavigation()
        setupUserAutocomplete()
    }

    private fun setupSetting() {
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics)
        this.guidelinePercent = 1 - px / size.y.toFloat()
    }

    private fun setupUserAutocomplete() {
        val edit = findViewById<EditText>(R.id.single)
        val elevation = 6f
        val backgroundDrawable: Drawable = ColorDrawable(Color.WHITE)
        val presenter = SuggestionPresenter(
            this,
            this@MainActivity,
            binding.viewmodel!!
        )
        val callback: AutocompleteCallback<Suggestion> = object :
            AutocompleteCallback<Suggestion> {
            override fun onPopupItemClicked(editable: Editable, item: Suggestion): Boolean {
                editable.clear()
                editable.append(item.title)
                return true
            }

            override fun onPopupVisibilityChanged(shown: Boolean) {}
        }
        val userAutocomplete = Autocomplete.on<Suggestion>(edit)
            .with(elevation)
            .with(backgroundDrawable)
            .with(presenter)
            .with(callback)
            .build()
    }

    private fun setupWebView() {
        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (isClearHistory) {
                    isClearHistory = false
                    view?.clearHistory()
                }
                super.onPageFinished(view, url)
            }
        }
    }

    private fun setupSlidingUpPanelLayout() {
        val slidingUpPanelLayout = findViewById<SlidingUpPanelLayout>(R.id.sliding_layout)
        val titleBar2 = findViewById<View>(R.id.title_bar2)
        val fullPanel = findViewById<View>(R.id.full_panel)
        val titleBar = findViewById<View>(R.id.title_bar)

        slidingUpPanelLayout.panelHeight += TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            54f,
            resources.displayMetrics
        ).toInt()

        slidingUpPanelLayout.addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                titleBar2.alpha = 1 - slideOffset
                fullPanel.alpha = slideOffset
                titleBar.alpha = slideOffset


                val bottomPercent = ((slideOffset - 1F) * (1F - guidelinePercent)) + 1F
                if (bottomPercent >= guidelinePercent) {
                    binding.guidelineBottom.setGuidelinePercent(bottomPercent)
                }
                println(bottomPercent)
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                println("$previousState  $newState")
                if (newState == SlidingUpPanelLayout.PanelState.ANCHORED) {
                    binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
                } else {
                    if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        titleBar2.visibility = View.INVISIBLE
                    } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                        titleBar.visibility = View.INVISIBLE
                    } else {
                        titleBar.visibility = View.VISIBLE
                        titleBar2.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupNavigation() {
        ServiceLocator.openSiteEvent.observe(this, EventObserver {
            upPanelExpandedAndLoadURL(it)
        })
    }

    private fun upPanelExpandedAndLoadURL(url: String) {
        binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        binding.webview.loadUrl(url)
    }

    fun upPanelHidden() {
        binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
        clearWebViewForward()
        binding.webview.loadUrl("about:blank")
    }

    fun upPanelCollapsed() {
        binding.slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

    private fun clearWebViewForward() {
        binding.webview.goBackOrForward(-binding.webview.copyBackForwardList().currentIndex)
        isClearHistory = true
    }
}

@BindingAdapter("layout_constraintGuide_begin")
fun setLayoutConstraintGuideBegin(guideline: Guideline, percent: Float) {
    val params = guideline.layoutParams as ConstraintLayout.LayoutParams
    params.guidePercent = percent
    guideline.layoutParams = params
}