package alg

type Line struct {
	Vertices [2]*Vec2
}

func (line *Line) One() *Vec2 {
	return line.Vertices[0]
}

func (line *Line) Two() *Vec2 {
	return line.Vertices[1]
}

func (line *Line) Norm() float64 {
	return line.Two().Sub(line.One()).Norm()
}

func (line *Line) Bounds() *Rect {
	one := line.One()
	two := line.Two()
	return NewRect(
		NewVec2(MinFloat64(one.X(), two.X()), MinFloat64(one.Y(), two.Y())),
		NewVec2(MaxFloat64(one.X(), two.X()), MaxFloat64(one.Y(), two.Y())),
	)
}

func NewLine(v1, v2 *Vec2) *Line {
	v1.Clone()
	return &Line{Vertices: [2]*Vec2{v1.Clone(), v2.Clone()}}
}
