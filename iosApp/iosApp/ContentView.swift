//
//  ContentView.swift
//  iosApp
//
//  Created by Наталья Троянова on 03.07.2024.
//

import Foundation

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all)
    }
}
