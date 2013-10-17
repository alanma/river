$(document).ready(function () {
    $('.pagination').jqPagination({
        link_string: '/?p={page_number}',
        max_page: 40,
        paged: function () {
            window.location = link_string;
        }
    });
});