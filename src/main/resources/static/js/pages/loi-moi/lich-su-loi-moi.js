$(window).click(function (e) {
    if ($(e.target).is($('#timeRange')) || $(e.target).is($('.btnTimeRange')) || $(e.target).is($('.timeRange'))) {
    } else {
        $('#timeRange').addClass("hidden")
    }
    if ($(e.target).is($('#timeRange2')) || $(e.target).is($('.btnTimeRange2')) || $(e.target).is($('.timeRange2'))) {
    } else {
        $('#timeRange2').addClass("hidden")
    }
});

function checkDate(start, end, dateStart, dateEnd, showDate, btnTimeRange) {

    if (start === '' && end !== '') {
        $('.err').html('Ngày bắt đầu chưa điền');
        return false;
    }
    if (start !== '' && end === '') {
        $('.err').html('Ngày kết thúc chưa điền');
        return false;
    }
    $('#' + showDate).html('').append('\n' +
        '<input type="date" disabled class="w-24 bg-white hiddenCalendar ' + btnTimeRange + ' cursor-pointer" id="' + dateStart + '"> - \n' +
        '<input type="date" disabled class="w-24 bg-white hiddenCalendar ' + btnTimeRange + ' cursor-pointer" id="' + dateEnd + '">')

    if (start === '' && end === '') {
        $('#' + showDate).html('')
    } else {
        $('#' + dateStart).val(start)
        $('#' + dateEnd).val(end)
    }
    return true;
}

$('#btnSearch').click(function () {
    var data = $('.lichSuDanhSach')
    var start = $('#start').val()
    var end = $('#end').val()
    var url = $(this).data('url') +
        'dateStart=' + start +
        '&type=' + (data.data('type') === undefined ? '' : data.data('type')) +
        '&dateEnd=' + end +
        '&search=' + $('#search').val()
    var type = 'danh-sach';
    search(url, type)
})

$('#btnSearch2').click(function () {
    var data = $('.lichSuThamGia')
    var start = $('#start2').val()
    var end = $('#end2').val()
    var url = $(this).data('url') +
        'dateStart=' + start +
        '&type=' + (data.data('type') === undefined ? '' : data.data('type')) +
        '&dateEnd=' + end +
        '&search=' + $('#search2').val() +
        '&status=' + $('#status').val()
    var type = 'tham-gia';
    search(url, type)
})

$('#date').click(function () {
    var start = $('#start').val();
    var end = $('#end').val();
    var typeCall = $('#type').val()
    var searchCall = $('#search').val()
    var url = $(this).data('url') + '?dateStart=' + start + '&dateEnd=' + end + '&type=' + typeCall + '&search=' + searchCall;
    var type = $(this).data('type');
    var dateStart = 'date1Start'
    var dateEnd = 'date1End'
    var showDate = 'showDate'
    var btnTimeRange = 'btnTimeRange'
    if (checkDate(start, end, dateStart, dateEnd, showDate, btnTimeRange) === true) {
        search(url, type)
    }
})

$('#date2').click(function () {
    var start = $('#start2').val();
    var status = $('#status').val();
    var end = $('#end2').val();
    var typeCall = $('#type').val()
    var searchCall = $('#search2').val()
    var url = $(this).data('url') + '?dateStart=' + start + '&dateEnd=' + end + '&type=' + typeCall + '&search=' + searchCall + '&status=' + status;
    var type = 'tham-gia';
    var dateStart = 'date2Start'
    var dateEnd = 'date2End'
    var showDate = 'showDate2'
    var btnTimeRange = 'btnTimeRange2'
    if (checkDate(start, end, dateStart, dateEnd, showDate, btnTimeRange) === true) {
        search(url, type)
    }
})

function search(url, type) {
    $.ajax({
        type: "GET",
        url: url,
        success: function (n) {
            if (type === 'danh-sach') {
                $('#lichSuDanhSach').html(n);
            }
            if (type === 'tham-gia') {
                $('#lichSuThamGia').html(n);
            }
        }
    })
}

$('#resetDate1').click(function () {
    $('#showDate').html('')
    $('#start').val('');
    $('#end').val('');
})
$('#resetDate2').click(function () {
    $('#showDate2').html('')
    $('#start2').val('');
    $('#end2').val('');
})

$('#type2').change(function () {
    var data = $('.lichSuThamGia')
    var start = $('#start2').val()
    var end = $('#end2').val()
    var url = $(this).data('url') +
        'search=' + (data.data('search') === undefined ? '' : data.data('search')) +
        '&status=' + (data.data('status') === undefined ? 1 : data.data('status')) +
        '&dateStart=' + start +
        '&dateEnd=' + end +
        '&type=' + $(this).val()
    var type = 'tham-gia'
    search(url, type)
})
$('#type').change(function () {
    var data = $('.lichSuDanhSach')
    var start = $('#start').val()
    var end = $('#end').val()
    var url = $(this).data('url') +
        'search=' + (data.data('search') === undefined ? '' : data.data('search')) +
        '&dateStart=' + start +
        '&dateEnd=' + end +
        '&type=' + $(this).val()
    var type = 'danh-sach'
    search(url, type)
})

$('#status').change(function () {
    var data = $('.lichSuThamGia')
    var url = $(this).data('url') +
        'search=' + (data.data('search') === undefined ? '' : data.data('search')) +
        '&type=' + (data.data('type') === undefined ? '' : data.data('type')) +
        '&dateStart=' + (data.data('dateStart') === undefined ? '' : data.data('dateStart')) +
        '&dateEnd=' + (data.data('dateEnd') === undefined ? '' : data.data('dateEnd')) +
        '&status=' + $(this).val()
    var type = 'tham-gia'
    search(url, type)
})