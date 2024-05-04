document.getElementById('imgInput').addEventListener('change', function(e) {
    var img = document.getElementById('previewImg');
    var file = e.target.files[0];
    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            img.style.display = 'block';
            img.src = e.target.result;
        }
        reader.readAsDataURL(file);
    } else {
        img.style.display = 'none';
    }
});
document.addEventListener('DOMContentLoaded', function() {
    var img = document.getElementById('previewImg');
    if (!img.src || img.src.trim() === "") {
        img.style.display = 'none';
    } else {
        img.style.display = 'block';
    }
});