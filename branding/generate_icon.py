"""Generate the original Croparium x Industrial Foregoing compatibility icon."""

from math import cos, pi, sin
from pathlib import Path

from PIL import Image, ImageDraw


S = 4
SIZE = 512 * S
im = Image.new("RGB", (SIZE, SIZE), "#101c29")
draw = ImageDraw.Draw(im)

for y in range(SIZE):
    t = y / (SIZE - 1)
    color = (
        round(17 + 2 * t),
        round(49 - 23 * t),
        round(62 - 18 * t),
    )
    draw.line((0, y, SIZE, y), fill=color)

def poly(points, color):
    draw.polygon([(round(x * S), round(y * S)) for x, y in points], fill=color)

def ellipse(box, color, width=0):
    coords = tuple(round(v * S) for v in box)
    if width:
        draw.ellipse(coords, outline=color, width=width * S)
    else:
        draw.ellipse(coords, fill=color)


# A simple machine ring and eight independent lugs, drawn from original geometry.
cx = cy = 256
for i in range(8):
    angle = 2 * pi * i / 8
    points = []
    for radius, delta in ((181, -0.10), (207, -0.10), (207, 0.10), (181, 0.10)):
        a = angle + delta
        points.append((cx + radius * cos(a), cy + radius * sin(a)))
    poly(points, "#6ac6bd")

ellipse((68, 68, 444, 444), "#6ac6bd")
ellipse((92, 92, 420, 420), "#132c38")
ellipse((118, 118, 394, 394), "#356a71", width=4)

# Balanced sprout with circuit-style roots: machinery feeding a living crop.
draw.line([(256 * S, 341 * S), (256 * S, 215 * S)], fill="#e1c272", width=23 * S)
poly([(256, 226), (220, 216), (173, 185), (184, 157), (216, 160), (256, 190)], "#a4dd99")
poly([(256, 226), (292, 216), (339, 185), (328, 157), (296, 160), (256, 190)], "#73cba9")
draw.line([(256 * S, 341 * S), (214 * S, 366 * S), (180 * S, 366 * S)], fill="#e1c272", width=8 * S)
draw.line([(256 * S, 341 * S), (298 * S, 366 * S), (332 * S, 366 * S)], fill="#e1c272", width=8 * S)
ellipse((162, 348, 198, 384), "#e1c272")
ellipse((314, 348, 350, 384), "#e1c272")

output = Path(__file__).with_name("icon.png")
im.resize((512, 512), Image.Resampling.LANCZOS).save(output)
print(output)
