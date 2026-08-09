function shortenUrl() {
    const url = document.getElementById("urlInput").value.trim();
    const alias = document.getElementById("aliasInput").value.trim();
    const result = document.getElementById("result");

    if (!url) {
        alert("Please enter a URL");
        return;
    }

    fetch("/api/shorten", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            url: url,
            alias: alias
        })
    })
    .then(async response => {
        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.error || "Something went wrong");
        }

        return data;
    })
    .then(data => {

        const shortUrl = data.shortUrl;

        result.classList.remove("hidden");

        result.innerHTML = `
            <div class="result-card">

                <p class="label">Your bite-sized link</p>

                <div class="short-link-box">

                    <a href="${shortUrl}"
                       target="_blank"
                       rel="noopener noreferrer">
                        ${shortUrl}
                    </a>

                    <button class="copy-btn"
                            onclick="copyLink('${shortUrl}')">
                        Copy
                    </button>

                </div>

                <div class="qr-section">

                    <h3>📱 Scan your link</h3>

                    <p>Take it anywhere. Share it with a scan.</p>

                    <div id="qrcode"></div>

                    <button class="download-qr-btn"
                            onclick="downloadQR()">
                        Download QR
                    </button>

                </div>

            </div>
        `;

        generateQRCode(shortUrl);
    })
    .catch(error => {

        result.classList.remove("hidden");

        result.innerHTML = `
            <div class="result-card">
                <p class="error-message">
                    ❌ ${error.message}
                </p>
            </div>
        `;
    });
}


/* Generate QR Code */

function generateQRCode(shortUrl) {

    const qrCode = document.getElementById("qrcode");

    qrCode.innerHTML = "";

    new QRCode(qrCode, {
        text: shortUrl,
        width: 180,
        height: 180,
        colorDark: "#1f2937",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H
    });
}


/* Copy Short URL */

function copyLink(shortUrl) {

    navigator.clipboard.writeText(shortUrl)
        .then(() => {
            alert("Bite-sized link copied! 🦷");
        })
        .catch(() => {
            alert("Failed to copy link.");
        });
}


/* Download QR Code */

function downloadQR() {

    const qrCode = document.getElementById("qrcode");
    const canvas = qrCode.querySelector("canvas");

    if (!canvas) {
        alert("QR code is not ready yet.");
        return;
    }

    const link = document.createElement("a");

    link.href = canvas.toDataURL("image/png");
    link.download = "biteu-qr-code.png";

    link.click();
}


/* =========================================
   Mobile nav toggle (new, purely presentational)
   ========================================= */

(function initMobileNav() {
    const toggle = document.getElementById("navToggle");
    const links = document.getElementById("navLinks");

    if (!toggle || !links) return;

    toggle.addEventListener("click", () => {
        const isOpen = links.classList.toggle("open");
        toggle.classList.toggle("open", isOpen);
        toggle.setAttribute("aria-expanded", String(isOpen));
    });

    links.querySelectorAll("a").forEach(link => {
        link.addEventListener("click", () => {
            links.classList.remove("open");
            toggle.classList.remove("open");
            toggle.setAttribute("aria-expanded", "false");
        });
    });
})();


/* =========================================
   Scroll reveal for sections (new, purely presentational)
   ========================================= */

(function initScrollReveal() {
    const targets = document.querySelectorAll(
        ".features, .about, .faq, .cta"
    );

    if (!targets.length) return;

    targets.forEach(el => el.classList.add("reveal"));

    if (!("IntersectionObserver" in window)) {
        targets.forEach(el => el.classList.add("is-visible"));
        return;
    }

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("is-visible");
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.15 });

    targets.forEach(el => observer.observe(el));
})();
