// Generate random colors
export function randomColor(usedColors) {
  const maxAttempts = 1000;
  const minColorDifference = 32; // Minimum difference between color components

  for (let attempt = 0; attempt < maxAttempts; attempt++) {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);

    let validColor = true;
    for (const color of usedColors) {
      const components = color
        .split(/[\s,()]+/)
        .filter(Boolean)
        .map(Number);
      const colorDifference =
        Math.abs(r - components[1]) +
        Math.abs(g - components[2]) +
        Math.abs(b - components[3]);
      if (colorDifference < minColorDifference) {
        validColor = false;
        break;
      }
    }

    if (validColor) {
      const a = 0.2; // Adjust the alpha (transparency) as needed
      const color = `rgba(${r}, ${g}, ${b}, ${a}`;
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
