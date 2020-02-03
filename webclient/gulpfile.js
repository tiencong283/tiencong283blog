var gulp          = require('gulp');
var browserSync   = require('browser-sync').create();
var $             = require('gulp-load-plugins')();
var autoprefixer  = require('autoprefixer');
var merge = require('merge-stream');

var sassPaths = [
  'node_modules/foundation-sites/scss',
  'node_modules/motion-ui/src'
];

// compile sass to css
function sass() {
  return gulp.src('src/foundation/scss/app.scss')
    .pipe($.sass({
      includePaths: sassPaths,
      outputStyle: 'compressed' // if css compressed **file size**
    })
      .on('error', $.sass.logError))
    .pipe($.postcss([
      autoprefixer({ browsers: ['last 2 versions', 'ie >= 9'] })
    ]))
    .pipe(gulp.dest('src/foundation/css'))
    .pipe(browserSync.stream());
};

// copy assets
gulp.task("copy-assets", function(){
  // https://github.com/gulpjs/gulp/blob/master/docs/recipes/using-multiple-sources-in-one-task.md
  return merge(
      gulp.src("src/foundation/css/**")
          .pipe(gulp.dest("target/classes/static/css")),

      gulp.src("src/foundation/foundation-icons/**")
          .pipe(gulp.dest("target/classes/static/foundation-icons")),

      gulp.src("src/foundation/images/**")
          .pipe(gulp.dest("target/classes/static/images")),

      gulp.src("src/foundation/js/**")
          .pipe(gulp.dest("target/classes/static/js")),
  );
});

// serving for preview
function serve() {
  browserSync.init({
    server: ["src/foundation/", "./"]
  });

  gulp.watch("src/foundation/scss/*.scss", sass);
  gulp.watch("*.html").on('change', browserSync.reload);
}

gulp.task('sass', gulp.series(sass, "copy-assets"));
gulp.task('default', gulp.series(sass, serve));