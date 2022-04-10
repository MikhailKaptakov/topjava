const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return fromISO(data);
                        }
                        return data;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                },

            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-meal-excess", data.excess);
            }
        })
    );

    const startDate =  jQuery('#startDate');
    const endDate =  jQuery('#endDate');
    const filterDateFormat = 'Y-m-d';

    jQuery(function()
    {
        startDate.datetimepicker({
            format: filterDateFormat,
            timepicker: false,
            onShow: function (ct) {
                this.setOptions({
                    maxDate: endDate.val() ? endDate.val() : false
                });
            }
        });
        endDate.datetimepicker({
            format: filterDateFormat,
            timepicker: false,
            onShow: function (ct) {
                this.setOptions({
                    minDate: startDate.val() ? startDate.val() : false
                });
            }
        });
    });

    const startTime =  jQuery('#startTime');
    const endTime =  jQuery('#endTime');
    const filterTimeFormat = 'H:i';

    jQuery(function()
    {
        startTime.datetimepicker({
            format: filterTimeFormat,
            datepicker: false,
            onShow: function (ct) {
                this.setOptions({
                    maxTime: endTime.val() ? endTime.val() : false
                });
            }
        });
        endTime.datetimepicker({
            format: filterTimeFormat,
            datepicker: false,
            onShow: function (ct) {
                this.setOptions({
                    minTime: startTime.val() ? startTime.val() : false
                });
            }
        });
    });
});

const modalDateTime = jQuery('#dateTime');
const modalDateTimeFormat = 'Y-m-d H:i'

modalDateTime.datetimepicker({
    format: modalDateTimeFormat
});

function fromISO(data) {
    data = data.replace("T", " ");
    data = data.slice(0, -3);
    return data;
}

function toISO(data) {
    data = data.replace(" ", "T");
    data = data + ":00";
    return data;
}
