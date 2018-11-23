$(document).ready(function () {
    bindEventCheckAllCheckbox('checkAll');
    enableOrDisableButtonDeleteAll();
    autoCheckCheckAll('checkAll');
});
function bindEventCheckAllCheckbox(id) {
    $('#' + id).on('change', function () {
       if ((this).checked) {
           $(this).closest('table').find('input[type=checkbox]').prop('checked', true);
       } else {
           $(this).closest('table').find('input[type=checkbox]').prop('checked', false);
       }
    });
}
function enableOrDisableButtonDeleteAll() {
    $('input[type=checkbox]').click(function() {
        if ($(this).attr('id') == 'checkAll' && $(this).prop('checked') == false) {
            $(this).closest('table').find('input[type=checkbox]').prop('checked', false);
        }
        if ($('input[type=checkbox]:checked').length > 0) {
            $('#deleteAll').prop('disabled', false);
        } else {
            $('#deleteAll').prop('disabled', true);
        }
    });
}
function autoCheckCheckAll(id) {
    var totalCheckbox = $('#' + id).closest('table').find('tbody input[type=checkbox]').length;
    var tableObj = $('#' + id).closest('table');
    $(tableObj).find('tbody input[type=checkbox]').each(function() {
        $(this).on('change', function () {
            var totalCheckboxChecked = $(tableObj).find('tbody input[type=checkbox]:checked').length;
            if (totalCheckbox == totalCheckboxChecked) {
                $('#' + id).prop('checked', true);
            } else {
                $('#' + id).prop('checked', false);
            }
        });
    });
}