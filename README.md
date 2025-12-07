# 阿里云实时语音识别 Android 示例

本示例基于阿里云官方 NUI SDK（实时语音识别）构建了一个最小可运行的 Android 应用，提供“开始收音”和“结束收音”按钮，并在界面上实时展示 ASR 结果。

> 提示：`app/build.gradle` 中的 SDK 版本号为占位值，请按照[阿里云官方文档](https://help.aliyun.com/zh/isi/developer-reference/nui-sdk-for-android)确认最新依赖坐标与版本号。

## 主要功能

- 请求麦克风权限后开始收音。
- 调用阿里云 ASR 接口进行实时转写，显示部分结果与最终结果。
- 提供随时结束收音的按钮。

## 快速开始

1. **准备凭证**：界面已预填本次提供的临时 Token，输入您的 AppKey 后即可启动。建议在正式环境通过安全方式下发 Token。
2. **确认依赖**：修改 `app/build.gradle` 中的 `com.alibaba.idst:nuisdk-android-asr` 版本号为官方推荐的最新版本。
3. **同步项目**：使用 Android Studio 打开项目，执行 Gradle 同步。
4. **运行**：将应用安装到 Android 设备（需提供网络和麦克风权限）。

## 代码结构

- `app/src/main/java/com/example/asrdemo/MainActivity.kt`：界面逻辑与 ASR 封装入口。
- `app/src/main/res/layout/activity_main.xml`：包含“开始收音”“结束收音”按钮与识别结果展示区。
- `app/build.gradle`：应用模块配置与 SDK 依赖。

## 注意事项

- 请在真实项目中补全 `AliyunAsrManager` 中标记的 `TODO` 部分，替换为官方 SDK 的初始化与回调逻辑。
- 如果使用混淆，请根据 SDK 文档在 `proguard-rules.pro` 添加保留规则。
- 由于使用了自定义 `BuildConfig` 字段（示例 Token），`android.buildFeatures.buildConfig` 已显式开启，保持该配置以避免构建错误。
