
#### 添加依赖

```gradle
dependencies {
    implementation 'com.classic.box:notification-box:0.0.3'
}
```

初始化
```gradle
// From java
NotificationBox.apply(new Config().installCrashMonitor(context));

// From kotlin
NotificationBox.apply(Config().installCrashMonitor(context))
```

#### TODO

- [x] 崩溃监控
- [ ] 卡顿/CPU/内存监控
- [ ] 内存泄漏监控