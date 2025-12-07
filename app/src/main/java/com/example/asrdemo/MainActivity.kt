package com.example.asrdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.asrdemo.databinding.ActivityMainBinding

/**
 * 使用阿里云实时语音识别 SDK 的简单演示应用。
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var asrManager: AliyunAsrManager

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                startRecognitionInternal()
            } else {
                Toast.makeText(this, R.string.permission_denied_message, Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        asrManager = AliyunAsrManager(this, object : AliyunAsrManager.Listener {
            override fun onReady() {
                binding.statusText.text = getString(R.string.status_ready)
            }

            override fun onPartialResult(text: String) {
                binding.resultText.text = text
                binding.statusText.text = getString(R.string.status_recognizing)
            }

            override fun onFinalResult(text: String) {
                binding.resultText.text = text
                binding.statusText.text = getString(R.string.status_done)
            }

            override fun onError(message: String) {
                binding.statusText.text = getString(R.string.status_error, message)
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }

            override fun onStopped() {
                binding.statusText.text = getString(R.string.status_stopped)
            }
        })

        binding.tokenInput.setText(BuildConfig.ASR_TOKEN)

        binding.appKeyInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                ensurePermissionAndStart()
                true
            } else {
                false
            }
        }

        binding.startButton.setOnClickListener {
            ensurePermissionAndStart()
        }

        binding.stopButton.setOnClickListener {
            asrManager.stopRecognition()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        asrManager.stopRecognition()
    }

    private fun ensurePermissionAndStart() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED ->
                startRecognitionInternal()

            shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) -> {
                Toast.makeText(this, R.string.permission_rationale, Toast.LENGTH_SHORT).show()
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }

            else -> permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    private fun startRecognitionInternal() {
        val token = binding.tokenInput.text?.toString().orEmpty().trim()
        val appKey = binding.appKeyInput.text?.toString().orEmpty().trim()
        if (token.isBlank() || appKey.isBlank()) {
            Toast.makeText(this, R.string.missing_credentials, Toast.LENGTH_SHORT).show()
            return
        }
        asrManager.startRecognition(token, appKey)
        binding.statusText.text = getString(R.string.status_connecting)
    }
}

/**
 * 阿里云语音识别管理器的最小封装。
 * SDK 初始化、开始/结束收音的具体调用请参考官方示例代码，将 TODO 部分替换为真实实现。
 */
class AliyunAsrManager(
    private val activity: AppCompatActivity,
    private val listener: Listener
) {

    interface Listener {
        fun onReady()
        fun onPartialResult(text: String)
        fun onFinalResult(text: String)
        fun onError(message: String)
        fun onStopped()
    }

    // TODO: 替换为真实的 NUI SDK 对象，例如 NlsClient 或 NuiSession 实例
    private var sdkSession: Any? = null

    fun startRecognition(token: String, appKey: String) {
        if (sdkSession != null) return

        // 以下流程需要根据 SDK 文档替换为实际代码。
        // 1. 构建识别参数，例如 appKey、token、采样率等。
        // 2. 创建 SDK 实例并设置回调，回调中调用 listener 的方法更新 UI。
        // 3. 开启麦克风收音并推送到 SDK。

        // 示例伪代码（请用真实 SDK API 替换）：
        // sdkSession = Nui.startSession(activity, token, appKey, object : NuiCallback { ... })

        // 演示效果：启动后立即回调 ready 状态。
        listener.onReady()
    }

    fun stopRecognition() {
        // TODO: 调用 SDK 的 stop/close 接口，并在完成后置空会话。
        sdkSession = null
        listener.onStopped()
    }
}
