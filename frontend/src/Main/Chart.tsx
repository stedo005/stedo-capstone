import React from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

interface dataForChartProps {
    chartQuantity: number[]
    chartSales: number[]
    chartLabels: string[]
}

export function Chart(props: dataForChartProps) {

     const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top' as const,
            },
            title: {
                display: true,
                text: 'Auswertung',
            },
        },
    }

    const labels = props.chartLabels

    const data = {
        labels,
        datasets: [
            {
                label: 'Anzahl',
                data: props.chartQuantity,
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
            },
            {
                label: 'Umsatz',
                data: props.chartSales,
                borderColor: 'rgb(53, 162, 235)',
                backgroundColor: 'rgba(53, 162, 235, 0.5)',
            },
        ],
    }

    return <Line options={options} data={data} />;
}