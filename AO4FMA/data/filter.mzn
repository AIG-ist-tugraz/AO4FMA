enum Accumulator = {h, l};
enum Resolution = {h, a, l};
enum FPS = {h, a, l};
enum Weight = {h, a, l};
enum Mount = {no, t1, t2};
enum Isolation = {h, l};

var Accumulator: accumulator;
var Resolution: resolution;
var FPS: fps;
var Weight: weight;
var Mount: mount;
var Isolation: isolation;

constraint landscape = true -> accumulator = Accumulator[h];
constraint portrait = true -> resolution = Resolution[h];
constraint sports = true -> fps = FPS[h];
constraint lightweight = true -> weight = Weight[l];
constraint exchangelens = true -> mount != Mount[no];
constraint waterproof = true -> isolation = Isolation[h];