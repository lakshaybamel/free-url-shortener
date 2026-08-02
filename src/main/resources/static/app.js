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
        body: JSON.stringify({ url: longUrl, alias: alias })
    })
    .then(async res => {
        if (!res.ok) {
            const errorMessage = await res.text();
            throw new Error(errorMessage || "Something went wrong.");
        }
        return res.json();
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
    .catch((error) => {
        result.classList.remove("hidden");
        result.innerText = error.message || "Something went wrong.";
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
