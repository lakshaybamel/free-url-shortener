function shortenUrl() {
    const input = document.getElementById("urlInput");
    const result = document.getElementById("result");
    const longUrl = input.value.trim();

    if (!longUrl) {
        alert("Please enter a URL");
        return;
    }

    fetch("/api/shorten", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ url: longUrl })
    })
    .then(res => res.json())
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
    .catch(() => {
        result.innerText = "Something went wrong.";
    });
}

function copyLink(link) {
    navigator.clipboard.writeText(link);
    alert("Copied to clipboard!");
}
function showAnalyticsComingSoon() {
    document.getElementById("analyticsModal").classList.remove("hidden");
}

function closeModal() {
    document.getElementById("analyticsModal").classList.add("hidden");
}

