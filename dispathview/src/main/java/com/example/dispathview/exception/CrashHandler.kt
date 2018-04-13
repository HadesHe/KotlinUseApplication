package com.example.dispathview.exception

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zhanghehe on 2018/4/13.
 */
class CrashHandler:Thread.UncaughtExceptionHandler{

    private var mDefaultCrashHandler: Thread.UncaughtExceptionHandler

    init {
        mDefaultCrashHandler=Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)

    }
    override fun uncaughtException(t: Thread?, e: Throwable) {

        dumpExceptionToSDCard(e)
        uploadExceptionToServer()
        e.printStackTrace()

        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t,e)
        }else{
            Process.killProcess(Process.myPid())
        }
    }

    private fun uploadExceptionToServer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun dumpExceptionToSDCard(e: Throwable) {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED).not()){
            if(DEBUG){

                Log.w(TAG,"sdcard unmounted,skip dump exception")
                return
            }
        }

        val dir= File(PATH)
        if (dir.exists().not()) {
            dir.mkdirs()
        }
        var current=System.currentTimeMillis()
        var time=SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(current))
        var file=File(PATH+FILE_NAME+time+FILE_NAME_SUFFIX)

        var pw=PrintWriter(BufferedWriter(FileWriter(file)))
        pw.println(time)
        dumpPhoneInfo(pw)
        pw.println()
        e.printStackTrace(pw)
        pw.close()

    }

    private fun dumpPhoneInfo(pw: PrintWriter) {
        var pm= mContext.packageManager
        var pi=pm.getPackageInfo(mContext.packageName,PackageManager.GET_ACTIVITIES)
        pw.print("App Version")
        pw.print(pi.versionName)
        pw.print('_')
        pw.println(pi.versionCode)

        pw.print("OS Version")
        pw.print(Build.VERSION.RELEASE)
        pw.print('_')
        pw.println(Build.VERSION.SDK_INT)

        pw.print("Verdor: ")
        pw.println(Build.MANUFACTURER)

        pw.print("Model: ")
        pw.println(Build.MODEL)

        pw.print("CPU ABI: ")
        pw.println(Build.CPU_ABI)
    }

    companion object {
        private lateinit var sInstance: CrashHandler

        private val DEBUG=true
        private val TAG="CrashHandler"
        private val PATH=Environment.getExternalStorageDirectory().path+"/CrashTest/log/"
        private val FILE_NAME="crash"
        private val FILE_NAME_SUFFIX=".trace"

        private lateinit var mContext: Context

         fun getInstance(context:Context): CrashHandler {
            if(sInstance==null){
                sInstance=CrashHandler()
                mContext=context
            }
            return sInstance
        }
    }

}