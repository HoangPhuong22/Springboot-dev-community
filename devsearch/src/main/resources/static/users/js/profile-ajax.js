$(document).ready(function() {
    // Gửi yêu cầu AJAX khi biểu mẫu tìm kiếm được gửi đi
    $('.form-ajax-profile').submit(function(event) {
        event.preventDefault();
        var keyword = $('.input.input--text').val();
        getPageProfile(1, keyword);
    });

    // Gửi yêu cầu AJAX khi người dùng nhấp vào các nút phân trang
    $(document).on('click', '.pagination-profile a', function(event) {
        event.preventDefault();
        var page = $(this).data('page');
        var keyword = $('.input.input--text').val();
        getPageProfile(page, keyword);
    });
});

function getPageProfile(page, keyword) {
    $.ajax({
        type: 'GET',
        url: '/profiles',
        data: {
            page: page,
            keyword: keyword
        },
        success: function(data) {
            // Cập nhật nội dung trang với dữ liệu mới từ yêu cầu AJAX
            $('.devlist').html($(data).find('.devlist').html());
            $('.pagination').html($(data).find('.pagination').html());
        },
        error: function() {
            alert('Đã xảy ra lỗi trong quá trình tải trang.');
        }
    });
}