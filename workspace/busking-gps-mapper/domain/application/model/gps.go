package model

import "busking.org/gps-mapper/alg"

type Gps struct {
	Loc       *alg.Vec2
	Timestamp int64
}
