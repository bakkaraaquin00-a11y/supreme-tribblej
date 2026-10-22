# VirtualCaster Website

Website statis berbahasa Indonesia dengan tema Dark Tech. Tidak membutuhkan Node.js, framework, database, atau proses build.

## Struktur
- `index.html` — halaman utama
- `assets/favicon.svg` — favicon
- `site.webmanifest` — metadata aplikasi web

## Uji lokal
Buka `index.html` di browser, atau jalankan server lokal:
`python -m http.server 8000`
Lalu kunjungi `http://localhost:8000`.

## Publikasi gratis — GitHub Pages
1. Buat akun GitHub jika belum punya.
2. Buat repository baru, misalnya `virtualcaster-website`.
3. Unggah seluruh isi folder ini ke root repository: `index.html`, `site.webmanifest`, dan folder `assets`.
4. Buka Settings → Pages.
5. Pada Build and deployment, pilih Deploy from a branch.
6. Pilih branch `main` dan folder `/(root)`, lalu Save.
7. Tunggu proses deployment selesai. URL biasanya berbentuk `https://USERNAME.github.io/virtualcaster-website/`.

## Alternatif gratis — Netlify
1. Masuk ke Netlify dan pilih opsi deploy manual / drag-and-drop.
2. Unggah folder proyek ini (atau ZIP yang menyertakannya).
3. Netlify akan memberikan subdomain gratis `*.netlify.app`.
4. Gunakan dashboard Netlify untuk mengubah nama subdomain jika masih tersedia.

## Sebelum rilis APK
- Build APK release dari proyek Android.
- Tandatangani APK dengan release keystore yang aman.
- Uji pada beberapa versi/perangkat Android.
- Scan file dan publikasikan checksum serta versi rilis.
- Unggah APK ke folder `downloads/` atau release hosting, lalu ubah CTA download di `index.html` agar menunjuk ke file rilis yang benar.
- Jangan pernah mengunggah keystore atau kata sandinya ke repository.

## Batasan
Situs ini adalah website statis. Form, analitik, autentikasi, dan backend belum dibuat. APK belum disertakan. MediaProjection tidak mengganti input kamera sistem Android secara langsung.
