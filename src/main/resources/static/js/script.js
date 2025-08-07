document.addEventListener('DOMContentLoaded', () => {
    // Translate tugmasi
    document.getElementById('translate').addEventListener('click', async () => {
        const fromText = document.querySelector('.from-text').value.trim();
        const fromLang = document.getElementById('from-lang').value;
        const toLang   = document.getElementById('to-lang').value;
        if (!fromText) return;

        const res  = await fetch(`/api/translate?text=${encodeURIComponent(fromText)}&from=${fromLang}&to=${toLang}`);
        const data = await res.json();
        document.querySelector('.tt').value = data.translated;
        const det = document.getElementById('detectedLang');
        if (det) det.textContent = `Detected: ${data.detectedSource || fromLang}`;
    });

    // Swap tugmasi
    document.getElementById('swap').addEventListener('click', () => {
        const ft = document.querySelector('.from-text');
        const tt = document.querySelector('.tt');
        [ft.value, tt.value] = [tt.value, ft.value];

        const fl = document.getElementById('from-lang');
        const tl = document.getElementById('to-lang');
        [fl.value, tl.value] = [tl.value, fl.value];
    });

    // Text-to-Speech
    function speak(text, lang) {
        const utter = new SpeechSynthesisUtterance(text);
        utter.lang = lang;
        speechSynthesis.speak(utter);
    }
    document.getElementById('from-speak').addEventListener('click', () => {
        const txt = document.querySelector('.from-text').value;
        speak(txt, document.getElementById('from-lang').value);
    });
    document.getElementById('to-speak').addEventListener('click', () => {
        const txt = document.querySelector('.tt').value;
        speak(txt, document.getElementById('to-lang').value);
    });

    // Clipboard copy
    document.getElementById('from-copy').addEventListener('click', () => {
        navigator.clipboard.writeText(document.querySelector('.from-text').value);
    });
    document.getElementById('to-copy').addEventListener('click', () => {
        navigator.clipboard.writeText(document.querySelector('.tt').value);
    });
});
