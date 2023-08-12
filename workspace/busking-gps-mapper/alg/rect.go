package alg

type Rect struct {
	Lt, Rb *Vec2
}

func NewRect(leftTop *Vec2, rightBottom *Vec2) *Rect {
	return &Rect{Lt: leftTop, Rb: rightBottom}
}

func NewRectCWH(center *Vec2, w, h float64) *Rect {
	hw := w / 2
	hh := h / 2
	return &Rect{Lt: NewVec2(center.X()-hw, center.Y()-hh), Rb: NewVec2(center.X()+hw, center.Y()+hh)}
}
