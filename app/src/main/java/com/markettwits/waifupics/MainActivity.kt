package com.markettwits.waifupics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.markettwits.core.wrappers.SaveAndRestore
import com.markettwits.core.wrappers.WrapBundle
import com.markettwits.core_ui.setContentLocal
import com.markettwits.waifupics.core.MenuNavGraph
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firstRun = savedInstanceState == null
        (application as SaveAndRestore).init(firstRun)
        setContentLocal(savedInstanceState){
            WaifuPicsTheme {
                MenuNavGraph()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (application as SaveAndRestore).save(WrapBundle.Base(outState))
        Log.d("mt05", "onSaveInstanceState #MainActivity")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle, ) {
        super.onRestoreInstanceState(savedInstanceState)
        (application as SaveAndRestore).restore(WrapBundle.Base(savedInstanceState))
        Log.d("mt05", "restoreState #MainActivity")
    }
}

