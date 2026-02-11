# KYAC Smart Energy - Android App

Kotlin ve Jetpack Compose kullanılarak geliştirilmiş akıllı enerji takip uygulaması.

## Özellikler

### 🎨 Animasyonlu Halkalar
- **Dış Halka (Cyan)**: Yıllık kalan enerji (1,260 kWh) - 20 saniyede tam tur
- **Orta Halka (Turuncu)**: Aylık kalan enerji (65 kWh, %80) - 15 saniyede tam tur
- **İç Halka (Kırmızı)**: Haftalık limit aşımı (-17 kWh, %40) - 10 saniyede tam tur

### ✨ Görsel Efektler
- Parlama (glow) efektleri
- Parçacık animasyonları
- Gradient renkler
- Akıcı dönüş animasyonları

### 🤖 Akıllı Asistan
- Gerçek zamanlı uyarılar
- Maliyet tahminleri
- Şarj önerileri

## Teknik Detaylar

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Dil**: Kotlin
- **UI Framework**: Jetpack Compose
- **Animasyon**: Compose Animation API

## Kurulum

### Gereksinimler
- Android Studio Hedgehog (2023.1.1) veya üzeri
- JDK 8 veya üzeri
- Android SDK 34

### Projeyi Çalıştırma

1. Projeyi Android Studio'da açın
2. Gradle senkronizasyonunu bekleyin
3. Bir Android cihaz veya emülatör seçin
4. Run butonuna basın

### Gradle Build

```bash
# Debug APK oluşturma
./gradlew assembleDebug

# Release APK oluşturma
./gradlew assembleRelease
```

## Proje Yapısı

```
app/
├── src/main/
│   ├── java/com/kyac/smartenergy/
│   │   ├── MainActivity.kt              # Ana aktivite
│   │   ├── EnergyDashboardScreen.kt     # Ana ekran ve animasyonlar
│   │   └── ui/theme/
│   │       ├── Theme.kt                 # Tema tanımlamaları
│   │       └── Type.kt                  # Tipografi
│   ├── res/
│   │   └── values/
│   │       └── strings.xml              # String kaynakları
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Animasyon Detayları

### Halka Dönüş Hızları
- **Dış Halka**: `LinearEasing`, 20000ms (yavaş)
- **Orta Halka**: `LinearEasing`, 15000ms (orta)
- **İç Halka**: `LinearEasing`, 10000ms (hızlı)

### Renk Paletleri
- **Cyan Gradient**: `#4DD0E1` → `#26C6DA` → `#00BCD4`
- **Orange Gradient**: `#FFB74D` → `#FFA726` → `#FF9800`
- **Red/Pink Gradient**: `#FF4081` → `#E91E63` → `#C2185B`

## Lisans

Bu proje KYAC Smart Energy için geliştirilmiştir.
