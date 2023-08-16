package model

import "busking.org/gps-mapper/alg"

type Location struct {
	Timestamp int64
	alg.Vec2
}
