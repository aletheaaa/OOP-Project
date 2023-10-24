// Generate random colors
export function randomColor(usedColors) {
  const maxAttempts = 1000;
  for (let attempt = 0; attempt < maxAttempts; attempt++) {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    const a = 0.2; // Adjust the alpha (transparency) as needed
    const color = `rgba(${r}, ${g}, ${b}, ${a}`;
    if (!usedColors.has(color)) {
      usedColors.add(color);
      return color;
    }
  }
  return "rgba(0, 0, 0, 0.2)"; // Return a default color if maxAttempts is exceeded
}

// Generate dynamic colors for charts
export function generateDoughnutColors(numDataPoints) {
  const usedColors = new Set();
  const dynamicBackgroundColors = [];
  const dynamicBorderColors = [];

  for (let i = 0; i < numDataPoints; i++) {
    const bgColor = randomColor(usedColors);
    dynamicBackgroundColors.push(bgColor);
    dynamicBorderColors.push(bgColor.replace("0.2", "1"));
  }

  return [dynamicBackgroundColors, dynamicBorderColors];
}
