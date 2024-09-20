$(document).ready(function() {
    // Gửi yêu cầu AJAX khi biểu mẫu tìm kiếm được gửi đi
    $('.form-ajax-project').submit(function(event) {
        event.preventDefault();
        var keyword = $('.input.input--text').val();
        getPageProject(1, keyword);
    });

    // Gửi yêu cầu AJAX khi người dùng nhấp vào các nút phân trang
    $(document).on('click', '.pagination-project a', function(event) {
        event.preventDefault();
        var page = $(this).data('page');
        var keyword = $('.input.input--text').val();
        getPageProject(page, keyword);
    });
});

function getPageProject(page, keyword) {
    $.ajax({
        type: 'GET',
        url: '/projects',
        data: {
            page: page,
            keyword: keyword
        },
        success: function(data) {
            // Cập nhật nội dung trang với dữ liệu mới từ yêu cầu AJAX
            $('.projectsList').html($(data).find('.projectsList').html());
            $('.pagination').html($(data).find('.pagination').html());
        },
        error: function() {
            alert('Đã xảy ra lỗi trong quá trình tải trang.');
        }
    });
}