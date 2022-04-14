import React from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import {Bar} from 'react-chartjs-2';

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

interface barChartProps {
    chartLabel?: string[]
    chartQuantity?: number[]
}

export function BarChart(props: barChartProps) {

    const options = {
        plugins: {
            title: {
                display: true,
                text: 'Chart.js Bar Chart - Stacked',
            },
        },
        responsive: true,
        scales: {
            x: {
                stacked: true,
            },
            y: {
                stacked: true,
            },
        },
    };

    const labels = props.chartLabel;

    const data = {
        labels,
        datasets: [
            {
                label: 'Dataset 1',
                data: props.chartQuantity,
                backgroundColor: 'rgb(255, 99, 132)',
            },
        ],
    };

    return <Bar options={options} data={data}/>;
}