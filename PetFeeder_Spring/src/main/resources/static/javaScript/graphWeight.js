const xArrayWeight = ["January", "February", "March", "April", "May","June","July","August","September","October","November","December"];
const yArrayWeight = [300,340, 340, 320,350,370,400,350,300,278,325,346];

const dataWeight = [{
    x: xArrayWeight,
    y: yArrayWeight,
    type: "graph",
    orientation: "v",
    marker: { color: "rgba(0,0,255,0.6)" }
}];

const layoutWeight = { title: "Food eaten" };

Plotly.newPlot("weight", dataWeight, layoutWeight );
