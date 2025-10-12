document.getElementById("staffForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;

  const res = await fetch("/api/staff", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email })
  });

  const data = await res.json();
  document.getElementById("qrDisplay").innerHTML = `
    <p>Staff Registered. ID: ${data.id}</p>
    <img src="data:image/png;base64,${data.qrCode}" />
  `;
});

function onScanSuccess(decodedText, decodedResult) {
  fetch("/api/attendance", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ personId: decodedText, type: "staff" })
  }).then(() => alert("Attendance Logged for ID: " + decodedText));
}

new Html5QrcodeScanner("reader", { fps: 10, qrbox: 250 }).render(onScanSuccess);