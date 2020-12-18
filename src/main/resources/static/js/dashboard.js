$(document).ready(function () {
    const dynamicColors = () => `rgb(${Math.random() *
        255 | 0}, ${Math.random() *
        255 | 0}, ${Math.random() *
        255 | 0})`;

    const chartError = (chart) => {
        chart.closest("div").append(`<em class="fas fa-exclamation-triangle fa-9x"></em>`);
        chart.remove();
    };

    $(".chart").each(function () {
        const url = $(this).attr("data-url");
        if (!url) {
            console.error(`data-url attribute must be present for canvas id="${$(this).attr("id")}"`);
            chartError($(this));
        } else {
            $.get(url)
                .done(resp => {
                    const config = {
                        type: 'pie',
                        data: {
                            datasets: [{
                                data: [],
                                backgroundColor: []
                            }],
                            labels: []
                        },
                        options: {
                            responsive: true
                        }
                    };
                    resp.data.forEach(element => {
                        config.data.datasets[0].data.push(element.value);
                        config.data.datasets[0].backgroundColor.push(dynamicColors());

                        if (element.name === true) {
                            element.name = "Ödünç Alınmış";
                        } else if (element.name === false) {
                            element.name = "Uygun";
                        }
                        config.data.labels.push(element.name);
                    });

                    new Chart($(this)[0].getContext('2d'), config);
                    $(this).removeClass("spinner-border");
                })
                .fail(() => {
                    chartError($(this));
                });
        }
    });
});