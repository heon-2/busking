package alg

import "math"

type Vec2 [2]float64

func (v *Vec2) X() float64 {
	return v[0]
}

func (v *Vec2) Y() float64 {
	return v[1]
}

func (v *Vec2) Clone() *Vec2 {
	clone := Vec2([2]float64{v[0], v[1]})
	return &clone
}

func (v *Vec2) Add(other *Vec2) *Vec2 {
	return v.Clone().AddSelf(other)
}

func (v *Vec2) Sub(other *Vec2) *Vec2 {
	return v.Clone().SubSelf(other)
}

func (v *Vec2) Scale(scalar float64) *Vec2 {
	return v.Clone().ScaleSelf(scalar)
}

func (v *Vec2) Dot(other *Vec2) float64 {
	return v.X()*other.X() + v.Y()*other.Y()
}

func (v *Vec2) Norm() float64 {
	return float64(math.Sqrt(float64(v.DotSelf())))
}

func (v *Vec2) Unit() *Vec2 {
	return v.Clone().UnitSelf()
}

func (v *Vec2) AddSelf(other *Vec2) *Vec2 {
	v[0] += other.X()
	v[1] += other.Y()
	return v
}

func (v *Vec2) SubSelf(other *Vec2) *Vec2 {
	v[0] -= other.X()
	v[1] -= other.Y()
	return v
}

func (v *Vec2) ScaleSelf(scalar float64) *Vec2 {
	v[0] *= scalar
	v[1] *= scalar
	return v
}

func (v *Vec2) DotSelf() float64 {
	return v.Dot(v)
}

func (v *Vec2) UnitSelf() *Vec2 {
	return v.ScaleSelf(1 / v.Norm())
}

func (v *Vec2) ProjectTo(u *Vec2, clip bool) *Vec2 {
	ul := u.Norm()
	uu := u.Scale(1 / ul)

	t := uu.Dot(v)
	if clip {
		t = MaxFloat64(0, MinFloat64(ul, t))
	}

	return uu.ScaleSelf(t)
}

func (v *Vec2) Radian(u *Vec2) float64 {
	return math.Acos(v.Dot(u) / (v.Norm()*u.Norm() + 1e-7))
}

func NewVec2(x, y float64) *Vec2 {
	v := Vec2([2]float64{x, y})
	return &v
}

func NewVec2FromFloat64Array(xy [2]float64) *Vec2 {
	return NewVec2(xy[0], xy[1])
}
