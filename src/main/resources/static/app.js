function shortenUrl() {
    const longUrl = document.getElementById("urlInput").value;

    if (!longUrl) {
        alert("Please enter a URL");
        return;
    }

    fetch("/api/shorten", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ url: longUrl })
    })
    .then(res => res.json())
    .then(data => {
        document.getElementById("result").innerHTML =
            `<p>Short URL:</p>
             <a href="${data.shortUrl}" target="_blank">${data.shortUrl}</a>`;
    })
    .catch(() => {
        document.getElementById("result").innerText = "Error shortening URL";
    });
}
