package alg

func MinFloat64(a, b float64) float64 {
	if a < b {
		return a
	} else {
		return b
	}
}

func MinFloat64s(values ...float64) float64 {
	min := values[0]
	for _, v := range values[1:] {
		min = MinFloat64(min, v)
	}
	return min
}

func MaxFloat64(a, b float64) float64 {
	if a < b {
		return b
	} else {
		return a
	}
}

func MaxFloat64s(values ...float64) float64 {
	max := values[0]
	for _, v := range values[1:] {
		max = MaxFloat64(max, v)
	}
	return max
}
