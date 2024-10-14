//
//  iosAppApp.swift
//  iosApp
//
//  Created by Наталья Троянова on 03.07.2024.
//

import SwiftUI
import WaifuPics

@main
struct iosAppApp: App {
    
    init() {
        WaifuPicsApp_IosKt.doInitLaunch()
        }
    
    var body: some Scene {
        WindowGroup {
           ContentView()
        }
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        WaifuPicsApp_IosKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea()
    }
}
