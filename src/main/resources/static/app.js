function shortenUrl() {
    const input = document.getElementById("urlInput");
    const aliasInput = document.getElementById("aliasInput");
    const result = document.getElementById("result");

    const longUrl = input.value.trim();
    const alias = aliasInput.value.trim();

    if (!longUrl) {
        alert("Please enter a URL");
        return;
    }

    fetch("/api/shorten", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            url: longUrl,
            alias: alias
        })
    })
    .then(async res => {
        const data = await res.json();

        if (!res.ok) {
            throw new Error(data.error || "Something went wrong");
        }

        return data;
    })
    .then(data => {
        const shortUrl = data.shortUrl;

        result.classList.remove("hidden");

        result.innerHTML = `
            <div class="result-card">
                <p class="label">Your short link</p>

                <div class="short-link-box">
                    <a href="${shortUrl}" target="_blank">${shortUrl}</a>
                    <button class="copy-btn" onclick="copyLink('${shortUrl}')">
                        Copy
                    </button>
                </div>

                <button class="analytics-btn" onclick="showAnalyticsComingSoon()">
                    View Analytics
                </button>
            </div>
        `;
    })
    .catch(error => {
        result.classList.remove("hidden");

        result.innerHTML = `
            <div class="result-card">
                <p class="error-message">❌ ${error.message}</p>
            </div>
        `;
    });
}

function copyLink(link) {
    navigator.clipboard.writeText(link)
        .then(() => {
            alert("Copied to clipboard!");
        })
        .catch(() => {
            alert("Failed to copy link.");
        });
}