const xArray = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday","Friday","Saturday"];
const yArray = [10, 10, 10, 10, 10,10,10];

const data = [{
    x: xArray,
    y: yArray,
    type: "graph",
    orientation: "v",
    marker: { color: "rgba(0,0,255,0.6)" }
}];

const layout = { title: "Food eaten" };

Plotly.newPlot("foodEaten", data, layout);
