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
        result.classList.remove("hidden");
        result.innerHTML = `
            <p><strong>Short URL:</strong></p>
            <a href="${data.shortUrl}" target="_blank">${data.shortUrl}</a>
            <br><br>
            <button onclick="copyLink('${data.shortUrl}')">Copy</button>
            <a href="/api/analytics/${data.shortUrl.split('/').pop()}" target="_blank">
                View Analytics
            </a>
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
