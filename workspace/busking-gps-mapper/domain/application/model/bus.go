package model

import (
	"strconv"

	"busking.org/gps-mapper/alg"
)

type BusId struct{ CompanyId, No int64 }

type Bus struct {
	Trace [][2]alg.Vec2
}

func (b *BusId) String() string {
	return strconv.FormatInt(b.CompanyId, 10) + ":" + strconv.FormatInt(b.No, 10)
}

func (b *Bus) Locate(loc, adjLoc alg.Vec2) {
	b.Trace = append(b.Trace, [2]alg.Vec2{loc, adjLoc})
}
