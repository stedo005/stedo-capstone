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
    chartSales: number[]
    chartLabels: string[]
}

export function LineChartEvaluateCategory(props: dataForChartProps) {

    const options = {
        responsive: true,
        plugins: {
            legend: {
                position: 'top' as const,
            },
            title: {
                display: true,
                text: 'Gesamtumsatz pro Tag',
            },
        },
    }

    const labels = props.chartLabels

    const data = {
        labels,
        datasets: [
            {
                label: 'Umsatz pro Tag',
                data: props.chartSales,
                borderColor: 'rgb(53, 162, 235)',
                backgroundColor: 'rgba(53, 162, 235, 0.5)',
            },
        ],
    }

    return <Line options={options} data={data} />;
}