<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Harmony Haven'a Hoşgeldiniz!</title>
    <style>
        /* Responsive CSS */
        @media only screen and (max-width: 600px) {
            .container {
                width: 100% !important;
            }
        }

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #e0e0e0;
        }

        .container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .header {
            background-color: #004d40; /* Koyu yeşil arka plan rengi */
            color: #ffffff;
            padding: 20px 0;
            text-align: center;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
        }

        .header img {
            width: 150px;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 0%;
        }

        .header h1 {
            margin: 10px 0;
            font-size: 24px;
        }

        .content {
            padding: 20px;
        }

        .content h2 {
            margin-top: 0;
            color: #004d40;
            font-size: 20px;
        }

        .content ul {
            list-style-type: none;
            padding: 0;
        }

        .content li {
            margin-bottom: 10px;
        }

        .content .special {
            color: #004d40; /* Aynı koyu yeşil renk */
        }

        .content p {
            margin: 10px 0;
            color: #333333;
        }

        .footer {
            background-color: #f1f1f1;
            padding: 15px;
            text-align: center;
            border-bottom-left-radius: 8px;
            border-bottom-right-radius: 8px;
        }

        .footer p {
            margin: 0;
            color: #666666;
        }
    </style>
</head>
<body>
<table role="presentation" cellspacing="0" cellpadding="0" border="0" align="center" width="600" class="container">
    <!-- Header -->
    <tr>
        <td class="header">
            <img src="http://harmonyhaven.erdemserhat.com/sources/article_images/harmony_haven_logo.png" alt="Harmony Haven Logo">
            <h1>Harmony Haven'a Hoşgeldin, ${name}!</h1>
            <p>Sizi aramızda görmekten büyük mutluluk duyuyoruz.</p>
        </td>
    </tr>
    <!-- Content -->
    <tr>
        <td class="content">
            <h2>Size neler sunduk?</h2>
            <ul>
                <li><strong class="special">Günlük Motivasyon:</strong> Ünlü yazarların sözlerinden derlenen günlük motivasyon yazıları ve alıntılar ile ilham alın.</li>
                <li><strong class="special">Kişisel Gelişim:</strong> Kendinizi geliştirmek ve başarılı olmak için araçlar ve kaynaklar keşfedin.</li>
                <li><strong class="special">Adınıza Özel Bildirimler:</strong> İlgi alanlarınıza göre kişiselleştirilmiş bildirimlerle size özel içerik ve öneriler alın.</li>
            </ul>
            <p>Harmony Haven'a katıldığınız için teşekkürler!</p>
            <p>Sorularınız veya geri bildirimleriniz mi var? Bu e-postayı yanıtlayarak bizimle iletişime geçebilirsiniz!</p>
        </td>
    </tr>
    <!-- Footer -->
    <tr>
        <td class="footer">
            <p><strong>En iyi dileklerimizle,</strong><br>Harmony Haven Ekibi</p>
            <p>Harmony Haven, kişisel gelişiminizi desteklemek ve size faydalı bilgiler sunmak amacıyla çalışan bir Türk girişimidir. Çevrenizle paylaşarak bu girişimin yayılmasına katkıda bulunabilirsiniz.</p>
        </td>
    </tr>
</table>
</body>
</html>
